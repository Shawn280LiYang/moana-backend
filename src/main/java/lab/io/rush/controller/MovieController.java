package lab.io.rush.controller;

import lab.io.rush.dto.MovieDto;
import lab.io.rush.dto.OrderDto;
import lab.io.rush.entity.Movie;
import lab.io.rush.entity.Order;
import lab.io.rush.service.OrderService;
import lab.io.rush.service.impl.EmailServiceImpl;
import lab.io.rush.service.MovieService;
import lab.io.rush.dto.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by liyang on 16/12/28.
 */

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private HttpSession httpSession;

    private final static String emailTitle = "Success Rush in Moana Ticket System";
    private final static Logger logger = LoggerFactory.getLogger(MovieController.class);

    @RequestMapping("/movieAll")
    public List<MovieDto> getMovieAll(){
        return movieService.getMovieAll();
    }

//    此接口暂时不需要,测试用
    @RequestMapping("/movie/{movieid}")
    public MovieDto getMovieById(@PathVariable Long movieid){
        return movieService.getMovieById(movieid);
    }

    @RequestMapping("/ticketStockAll")
    public Map getTicketStockAll(){
        Map result = new HashMap();

        List<Movie> movieList = movieService.getMovieALlNoTag();

        if(movieList==null || movieList.size()==0){
            result.put("responseCode", Code.DATA_NOT_FOUND);
            result.put("responseMsg", "未查询到,请检测数据库连接或电影库为空");
        }else{
            List<Map> stockList = new ArrayList<>();
            for(int i=0;i<movieList.size();i++){
                Map stock = new HashMap<>();
                stock.put(movieList.get(i).getId(), movieService.getStock(movieList.get(i).getId()));
                stockList.add(stock);
            }
            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("data", stockList);
        }

        return result;
    }

//    此接口暂时不需要,测试用
    @RequestMapping("/ticketStock/{movieid}")
    public Map getTicketStock(@PathVariable Long movieid){
        Map result = new HashMap();

        Movie movie = movieService.getMovieNoTag(movieid);

        if(movie==null){
            result.put("responseCode", Code.DATA_NOT_FOUND);
            result.put("responseMsg", "未查询到,请检测数据库连接及电影ID");
        }else{
            int stock = movieService.getStock(movieid);
            result.put("responseCode", Code.COMMON_SUCCESS);
            result.put("data", stock);
        }

        return result;
    }

    //cache测试
    @RequestMapping("/testCache")
    public void testCache(){
        List<Movie> movieList1 = movieService.getMovieALlNoTag();

        if(movieList1==null || movieList1.size()==0){
        }else{
            Map stockList1 = new HashMap<>();
            System.out.println(System.currentTimeMillis());
            for(int i=0;i<movieList1.size();i++){
                stockList1.put(movieList1.get(i).getId(),movieService.getStock(movieList1.get(i).getId()));
            }
            System.out.println(System.currentTimeMillis());
            System.out.println("stockList1: "+stockList1);
        }

        List<Movie> movieList2 = movieService.getMovieALlNoTag();

        if(movieList2==null || movieList2.size()==0){
        }else{
            Map stockList2 = new HashMap<>();
            System.out.println(System.currentTimeMillis());
            for(int i=0;i<movieList2.size();i++){
                stockList2.put(movieList2.get(i).getId(),movieService.getStock(movieList2.get(i).getId()));
            }
            System.out.println(System.currentTimeMillis());
            System.out.println("stockList2: "+stockList2);
        }
    }

    @RequestMapping("/rush/{movieid}")
    public Map makeOrder(@PathVariable Long movieid){
        Map result = new HashMap();

        //判断:每个用户只能买两张相同的电影票
        List<OrderDto> purchased = orderService.findByMovieidAndUserid(movieid,(Long)httpSession.getAttribute("uid"));
        if(purchased==null){
            result.put("responseCode",Code.DB_EXCEPTION);
            result.put("responseMsg","数据库连接错误");
            return result;
        }
        if(purchased!=null & purchased.size() >= 20){ // TODO 正式版本改成2
            result.put("responseCode",Code.COMMON_FAIL);
            result.put("responseMsg","同一电影票同一用户不能买超过20张");
            return result;
        }

        Order order = new Order(movieid, (Long)httpSession.getAttribute("uid"));

        if(movieService.getStock(movieid) > 0){
            try {
                Movie movie = movieService.getMovieNoTag(movieid);
                movie.setTicketstock(movieService.getStock(movieid)-1);
                movieService.merge(movie);

                order.setCreatetime(new Date());
                orderService.persist(order);

                result.put("responseCode", Code.COMMON_SUCCESS);
            }catch (Exception e) {
                e.printStackTrace();
                result.put("responseCode", Code.DB_EXCEPTION);
                result.put("responseMsg", "数据库操作错误");
            }
        }else{
            result.put("responseCode",Code.COMMON_FAIL);
            result.put("responseMsg","电影票库存不足");
        }

        //发送邮件
        if(result.get("responseCode").equals(Code.COMMON_SUCCESS)){
            String emailContent = emailService.createEmailContent(order);

            try{
                emailService.sendEmail((String)httpSession.getAttribute("email"),emailTitle,emailContent);
            }catch (Exception e){
                e.printStackTrace();
                result.replace("responseCode",Code.EMAIL_FAIL);
                result.put("responseMsg","邮件发送错误");
            }
        }

        return result;
    }
}

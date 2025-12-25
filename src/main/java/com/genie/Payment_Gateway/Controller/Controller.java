package com.genie.Payment_Gateway.Controller;


import com.genie.Payment_Gateway.Service.paymentService;
import com.genie.Payment_Gateway.entity.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(
    origins = {
        "http://127.0.0.1:5500",
        "https://paytem-by.vercel.app",
        "https://payment-fe-chi.vercel.app"
    },
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class Controller {
    @Autowired
    private paymentService paymentService;


    @PostMapping("/create-Order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentOrder Order) {
        try {
            String serviceOrder = paymentService.createOrder(Order);
            return ResponseEntity.ok(serviceOrder);
        }
        catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error Creating an Order");
        }
    }

   @PostMapping("/update-Order")
    public ResponseEntity<String> updateOrderStatus(@RequestParam String paymentId,@RequestParam String OrderId,@RequestParam String status){
       try {
           paymentService.updateOrderStatus(paymentId, OrderId, status);
           System.out.println("Email Sent Successfully");
           return ResponseEntity.ok("Order Updated Successfully and Email Sent");
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Error updating order: " + e.getMessage());
       }

    }
}




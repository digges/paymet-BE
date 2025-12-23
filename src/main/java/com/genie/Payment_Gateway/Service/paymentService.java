package com.genie.Payment_Gateway.Service;


import com.genie.Payment_Gateway.entity.PaymentOrder;
import com.genie.Payment_Gateway.repo.PaymentRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class paymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;


    @Autowired
    PaymentRepo paymentRepo;


    @Autowired
    EmailService emailService;

    public String createOrder(PaymentOrder Orderdetails) throws RazorpayException {
        RazorpayClient client=new RazorpayClient(keyId,keySecret);
        JSONObject orderReq=new JSONObject();
        //create the req for the razorpay
        orderReq.put("amount",(int)(Orderdetails.getAmount()*100));
        orderReq.put("currency","INR");
        orderReq.put("receipt","txn_"+ UUID.randomUUID());
     // go and come with Order Details from razorpay
        Order razorpayOrder=client.orders.create(orderReq);
        System.out.println(razorpayOrder.toString());
        Orderdetails.setOrderId(razorpayOrder.get("id"));
        Orderdetails.setStatus("CREATED");
        Orderdetails.setCreatedAt(LocalDateTime.now());



        //store in the db
        paymentRepo.save(Orderdetails);
        return razorpayOrder.toString();

    }

    public void updateOrderStatus(String paymentId, String orderId, String status) {
        PaymentOrder order = paymentRepo.findByOrderId(orderId);
        order.setPaymentId(paymentId);
        order.setStatus(status);
        paymentRepo.save(order);
        if("SUCCESS".equalsIgnoreCase(status)){
            emailService.sendEmail(order.getEmail(),order.getName(), order.getCourseName(),order.getAmount());
        }
    }
}

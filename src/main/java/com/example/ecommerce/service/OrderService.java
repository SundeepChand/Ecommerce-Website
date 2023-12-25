package com.example.ecommerce.service;

import com.example.ecommerce.dto.checkout.CheckoutItemDto;
import com.example.ecommerce.models.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    public Session createStripeSession(User user, List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        Stripe.apiKey = apiKey;

        String successURL = baseURL + "/payment/success";
        String failureURL = baseURL + "/payment/failure";

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (CheckoutItemDto checkoutItemDto: checkoutItemDtoList) {
            sessionItemList.add(convertCheckoutItemDtoToSessionLineItem(checkoutItemDto));
        }

        Customer customer = createStripeCustomerIdempotent(user);

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .setSuccessUrl(successURL)
                .addAllLineItem(sessionItemList)
                .setCustomer(customer.getId())
                .build();

        return Session.create(sessionCreateParams);
    }

    private Customer createStripeCustomerIdempotent(User user) throws StripeException {
        try {

            return Customer.retrieve(user.getEmail());

        } catch (StripeException e) {
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setAddress(
                            CustomerCreateParams.Address.builder()
                                    .setLine1("Flat-1, D Block")
                                    .setLine2("Vaswani Menlo Park, Thubarahalli")
                                    .setCity("Bengaluru")
                                    .setState("Karnataka")
                                    .setCountry("Bangladesh")
                                    .setPostalCode("560066")
                                    .build()
                    )
                    .build();

            return Customer.create(customerCreateParams);
        }
    }

    private SessionCreateParams.LineItem convertCheckoutItemDtoToSessionLineItem(
            CheckoutItemDto checkoutItemDto
    ) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("INR")
                                .setUnitAmount((long) (checkoutItemDto.getPrice()) * 100)
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(checkoutItemDto.getProductName())
                                                .build()
                                )
                                .build()
                )
                .setQuantity((long) checkoutItemDto.getQuantity())
                .build();
    }
}

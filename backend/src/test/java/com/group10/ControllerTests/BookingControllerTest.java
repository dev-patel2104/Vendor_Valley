package com.group10.ControllerTests;


import com.group10.Controller.BookingController;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.RequestBooking;
import com.group10.Service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookingController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookingService bookingService;


    @Test
    public void requestReservationTest() throws Exception {
        when(bookingService.requestReservation(Mockito.anyString(), Mockito.any(RequestBooking.class))).thenReturn(true);
        mockMvc.perform(post("/booking").header("jwtToken", "my jwt token").contentType("application/json").content("{\"bookingId\": 2}")).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void respondToBookingRequestTest() throws Exception {
        when(bookingService.respondToBooking(Mockito.anyString(), Mockito.any(BookingResponseRequest.class))).thenReturn(true);
        mockMvc.perform(post("/booking/respond").header("jwtToken", "my jwt token").contentType("application/json").content("{\"bookingID\": 666}")).andExpect(status().is2xxSuccessful());
    }

}

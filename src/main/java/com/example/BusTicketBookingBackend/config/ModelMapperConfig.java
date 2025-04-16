package com.example.BusTicketBookingBackend.config;

import com.example.BusTicketBookingBackend.dtos.response.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);


        return modelMapper;
    }


}


package edu.unifalmg.monolithecommerce.payment.application.dto.commands;

import java.util.UUID;

public record GetPaymentByIdCommand (UUID paymentId){
}

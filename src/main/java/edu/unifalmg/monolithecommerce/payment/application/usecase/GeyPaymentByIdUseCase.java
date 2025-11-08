package edu.unifalmg.monolithecommerce.payment.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import edu.unifalmg.monolithecommerce.payment.application.dto.PaymentDTO;
import edu.unifalmg.monolithecommerce.payment.application.dto.commands.GetPaymentByIdCommand;
import edu.unifalmg.monolithecommerce.payment.application.dto.mapper.PaymentMapper;
import edu.unifalmg.monolithecommerce.payment.application.port.in.GetPaymentByIdPort;
import edu.unifalmg.monolithecommerce.payment.application.port.out.PaymentRepositoryPort;
import edu.unifalmg.monolithecommerce.payment.domain.model.Payment;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GeyPaymentByIdUseCase implements GetPaymentByIdPort {

    private final PaymentRepositoryPort paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO execute(GetPaymentByIdCommand cmd){
        Payment payment = paymentRepository.findById(cmd.paymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment with this paymentId not found"));

        return paymentMapper.toDTO(payment);
    }


}

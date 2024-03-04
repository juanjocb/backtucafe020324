package com.backtucafe.service;

import com.backtucafe.controller.response.TokenResponse;
import com.backtucafe.model.Business;
import com.backtucafe.model.Client;
import com.backtucafe.model.request.LoginRequest;
import com.backtucafe.model.request.RegisterRequest;
import com.backtucafe.model.request.UpdateBusinessRequest;
import com.backtucafe.model.request.UpdateClientRequest;
import com.backtucafe.repository.BusinessRepository;
import com.backtucafe.security.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final JavaMailSender javaMailSender;

    public String registerBusiness(RegisterRequest request) throws MessagingException {
        Business business = Business.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        businessRepository.save(business);

        sendRegistrationEmail(business.getEmail(), business.getName());
        return "Te has registrado con exito";
    }
    public void sendRegistrationEmail(String to, String name) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("!Registro Exitoso¡");

        // HTML del contenido del correo electrónico
        String htmlContent = "<div style='background-image: url(\"cid:background\"); background-size: cover; background-position: center; background-repeat: repeat; background-color: rgba(255, 255, 255, 0.3); height: 500px; display: flex; justify-content: center; align-items: center;'>" +
                "<div style='text-align: center; color: black;'>" +
                "<p style='font-size: 24px; font-weight: bold; color: black;'>¡Hola " + name + ",!</p><br>" +
                "<p style='font-size: 16px; color: black;'>¡Bienvenido a TuCafe!</p>"+
                "<p style='font-size: 14px; color: black;'>Gracias por registrarte en TuCafe! Ahora podrás disfrutar de esta maravillosa experiencia.</p>" +
                "<img src='cid:logo' width='150' style='border: 3px solid #663300; border-radius: 50%;'>" +
                "<p style='font-size: 14px; color: white;'>Queremos agradecerte por registrarte con nosotros y confiar en nuestra plataforma para disfrutar de una experiencia única. Estamos emocionados de tenerte a bordo y estamos seguros de que te encantará lo que tenemos preparado para ti." +
                "<br>" +
                "¡Gracias por unirte a nuestra comunidad de amantes del café!" +
                "<br>" +
                "Atentamente," +
                "<b style='color: black;'>TuCafe</b></p>"+
                "</div>" +
                "</div>";

        String cssStyles = "<style>" +
                "@media screen and (max-width: 768px) {" +
                "   div { height: auto !important; }" +
                "   p { font-size: 14px !important; }" +
                "   .logo { max-width: 40px !important; }" +
                "</style>";


        htmlContent = cssStyles + htmlContent;


        helper.setText(htmlContent, true);

        ClassPathResource backgroundImg = new ClassPathResource("images/fondo.jpeg");
        helper.addInline("background", backgroundImg);

        ClassPathResource logoImg = new ClassPathResource("images/tucafe2.jpeg");
        helper.addInline("logo", logoImg);


        javaMailSender.send(message);




    }
    public TokenResponse loginBusiness(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Business business = businessRepository.findByEmail(request.getEmail());
        String token = tokenUtils.getTokenBusiness(business);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    public Business updateProfBusiness(Long id_business, UpdateBusinessRequest request){
        Business business = businessRepository.findById(id_business).orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
        if (business != null){
            if (request.getName() != null){
                business.setName(request.getName());
            }

            if (request.getDescription() != null){
                business.setDescription(request.getDescription());
            }

            if (request.getAddress() != null){
                business.setAddress(request.getAddress());
            }

            if (request.getPhone() != null){
                business.setPhone(request.getPhone());
            }
            if (request.getImage() != null){
                business.setImage(request.getImage());
            }

            if (request.getPassword() != null){
                String newPassowrd = passwordEncoder.encode(request.getPassword());
                business.setPassword(newPassowrd);
            }

            if (request.getCity() != null){
                business.setCity(request.getCity());
            }

            if (request.getStart_hour() != null){
                business.setStart_hour(request.getStart_hour());
            }

            if (request.getFinish_hour() != null){
                business.setFinish_hour(request.getFinish_hour());
            }

            businessRepository.save(business);

            return business;
        }else{
            return null;
        }

    }



}

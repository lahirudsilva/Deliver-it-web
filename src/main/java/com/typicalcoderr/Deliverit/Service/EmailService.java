package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 12:58 PM
 */
@Component
@AllArgsConstructor
public class EmailService {
        private final JavaMailSender emailSender;

        @Async
        public void sendSimpleMessage(ShipmentDto dto) throws MessagingException{

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

//            String sender = dto.getReceiverEmail();


            //Body of email. In HTML format including CSS
            String htmlMsg = "<html>\n"+
                    "  <head>\n" +
                    "    <style>\n" +
                    "    </style>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <h2>Message from Deliverit</h2>\n" +
                    "\n" +
                    "    <table>\n" +
                    "      <tr>\n" +
                    "        <th>Email of Sender</th>\n" +
                    "        <td class=\"ln\"><b>"+dto.getSenderEmail()+"</b></td>\n" +
                    "      </tr>\n" +
                    "      <tr>\n" +
                    "        <th>Body</th>\n" +
                    "        <td>"+"Your Shipment has been Rejected"+"</td>\n" +
                    "      </tr>\n" +
                    "    </table>\n" +
                    "  </body>\n" +
                    "</html>";
            try {
                // Set other attributes
                helper.setText(htmlMsg, true);
                helper.setTo(dto.getSenderEmail());
                helper.setTo(dto.getReceiverEmail());
                helper.setSubject("\t&copy; Deliverit Notification service");
                helper.setFrom("thebloginofficial@gmail.com");


            } catch (MessagingException e){
                e.printStackTrace();
                throw e;
            }
            emailSender.send(mimeMessage);
        }
}

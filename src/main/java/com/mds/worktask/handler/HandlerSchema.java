package com.mds.worktask.handler;

import com.mds.worktask.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import org.springframework.web.reactive.result.view.HttpMessageWriterView;
import reactor.core.publisher.Mono;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

@Component
public class HandlerSchema {

    @Value("classpath:/test.xml")
    private Resource indexHtml;

    public Mono<ServerResponse> schema(ServerRequest request) {

        Message message = new Message();
        message.setMessage("hello xml");
        BodyInserter<Object, ReactiveHttpOutputMessage> body =
                BodyInserters.fromValue(encodeBean(message));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(body);
    }
    public Mono<ServerResponse> schemaConditionGet(ServerRequest request) {
        Message message = new Message();
        message.setMessage("hello");
        BodyInserter<Object, ReactiveHttpOutputMessage> body =
                BodyInserters.fromValue(message);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
    public Mono<ServerResponse> schemaConditionPost(ServerRequest request) {
        BodyInserter<String, ReactiveHttpOutputMessage> body =
                BodyInserters.fromValue("Hello, Condition Post!");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    public static String encodeBean(Object bean){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(out);
        encoder.writeObject(bean);
        encoder.close();
        return out.toString();
    }

    public static Object decodeBean(String xml){
        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(new ByteArrayInputStream(xml.getBytes("GBK")));
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}

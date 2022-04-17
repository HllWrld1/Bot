package com.simonenko.demo;

import com.simonenko.demo.Entity.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import com.simonenko.demo.Entity.ChatState;

import com.simonenko.demo.Entity.User;
import com.simonenko.demo.Repository.ChatStateRepository;
import com.simonenko.demo.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
public class HandlerAge {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public void handlerAge(Long chatId, Message message) {
        String text = "Какой твой рост (см)?";
        SendMessage response = new SendMessage();

        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        int me = Integer.parseInt(message.getText());
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.changeState(chatId, DialogState.ENTERHEIGHT.toString());
            userRepository.changeAge(chatId, me);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }

    }
}

package com.simonenko.demo;

import com.simonenko.demo.Entity.ChatState;

import com.simonenko.demo.Entity.User;
import com.simonenko.demo.Repository.ChatStateRepository;
import com.simonenko.demo.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

@Component
class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final String token;
    private final String username;

    @Autowired
    private ChatStateRepository chatStateRepository;
    @Autowired
    private UserRepository userRepository;

    Bot(@Value("${bot.token}") String token, @Value("${bot.username}") String username) {
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            if (!chatStateRepository.existsById(chatId)) {
                handlerNewUser(chatId);
            } else {
                String state = chatStateRepository.getChatStateById(chatId);
                switch (state) {
                    case "ENTERNAME":
                        handlerName(chatId, message);
                        break;
                    case "ENTERAGE":
                        handlerAge(chatId, message);
                        break;
                    case "ENTERHEIGHT":
                        handlerHeight(chatId, message);
                        break;
                    case "ENTERWEIGHT":
                        handlerWeight(chatId, message);
                        break;
                    case "ENTERMENU": {
                        handlerMenu(chatId, message);
                        break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getFrom().getId();
            System.out.println(chatId);
            String state = chatStateRepository.getChatStateById(chatId);
            switch (state) {
                case "ENTERGOAL" ->
                    handlerGoal(chatId, callbackQuery);


                case "ENTERACTIVITY" ->
                    handlerActivity(chatId, callbackQuery);


            }
        }
    }


    public void handlerNewUser(Long chatId) {
        String text = "Привет! Меня зовут EdaBot! Как тебя зовут?";
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.save(new ChatState(chatId, DialogState.ENTERNAME.toString()));
            userRepository.save(new User(chatId));
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public void handlerName(Long chatId, Message message) {
        String text = "Сколько тебе лет?";
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.changeState(chatId, DialogState.ENTERAGE.toString());
            userRepository.changeName(chatId, message.getText());
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }

    }

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

    public void handlerHeight(Long chatId, Message message) {
        String text = "Какой твой вес? (кг) ";
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        int me = Integer.parseInt(message.getText());
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.changeState(chatId, DialogState.ENTERWEIGHT.toString());
            userRepository.changeHeight(chatId, me);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public void handlerWeight(Long chatId, Message message) {
        String text = "Теперь выбери число, совпадающее с твоей активностью: \n\n" +
                "1.2 – минимальная активность, сидячая работа, не требующая значительных физических нагрузок;\n" +
                "1.375 – слабый уровень активности: интенсивные упражнения не менее 20 минут один-три раза в неделю. Это может быть езда на велосипеде, бег трусцой, баскетбол, плавание, катание на коньках и т. д. Если вы не тренируетесь регулярно, но сохраняете занятый стиль жизни, который требует частой ходьбы в течение длительного времени, то выберите этот коэффициент;\n" +
                "1.55 – умеренный уровень активности: интенсивная тренировка не менее 30-60 мин три-четыре раза в неделю (любой из перечисленных выше видов спорта);\n" +
                "1.7 – тяжелая и трудоемкая активность: интенсивные упражнения и занятия спортом 5-7 дней в неделю. Трудоемкие занятия также подходят для этого уровня, они включают строительные работы (кирпичная кладка, столярное дело и т. д.), занятость в сельском хозяйстве и т. п.;\n" +
                "1.9 – экстремальный уровень: включает чрезвычайно активные и/или очень энергозатратные виды деятельности: занятия спортом с почти ежедневным графиком и несколькими тренировками в течение дня; очень трудоемкая работа, например, сгребание угля или длительный рабочий день на сборочной линии. Зачастую этого уровня активности очень трудно достичь.\n";

        SendMessage response = new SendMessage();

        response.setChatId(String.valueOf(chatId));

        response.setText(text);

        response.setReplyMarkup(handlerWeightSetBotton());

        int me = Integer.parseInt(message.getText());
        System.out.println(me);
        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.changeState(chatId, DialogState.ENTERACTIVITY.toString());
            userRepository.changeWeight(chatId, me);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public InlineKeyboardMarkup handlerWeightSetBotton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("1.2");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("1.375");
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("1.55");
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("1.7");
        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("1.9");
        button1.setCallbackData("1.2");
        button2.setCallbackData("1.375");
        button3.setCallbackData("1.55");
        button4.setCallbackData("1.7");
        button5.setCallbackData("1.9");
        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(button1);
        keyboardButtonList1.add(button2);
        List<InlineKeyboardButton> keyboardButtonList2 = new ArrayList<>();
        keyboardButtonList2.add(button3);
        keyboardButtonList2.add(button4);
        List<InlineKeyboardButton> keyboardButtonList3 = new ArrayList<>();
        keyboardButtonList3.add(button5);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(keyboardButtonList1);
        list.add(keyboardButtonList2);
        list.add(keyboardButtonList3);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    public void handlerActivity(Long chatId, CallbackQuery callbackQuery) {
        String text = "Какова твоя цель?\n";
        SendMessage response = new SendMessage();
        response.setText(text);
        response.setChatId(String.valueOf(chatId));
        response.setReplyMarkup(handlerActivitySetBotton());

        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            chatStateRepository.changeState(chatId, DialogState.ENTERGOAL.toString());
            userRepository.changeActivity(chatId, callbackQuery.getData());
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public InlineKeyboardMarkup handlerActivitySetBotton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Похудеть");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Поддерживать свой вес");
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Набрать массу");
        button1.setCallbackData("1");
        button2.setCallbackData("2");
        button3.setCallbackData("3");
        List<InlineKeyboardButton> keyboardButtonList1 = new ArrayList<>();
        keyboardButtonList1.add(button1);
        keyboardButtonList1.add(button2);
        List<InlineKeyboardButton> keyboardButtonList3 = new ArrayList<>();
        keyboardButtonList3.add(button3);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(keyboardButtonList1);
        list.add(keyboardButtonList3);
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    public void handlerGoal(Long chatId, CallbackQuery callbackQuery) {
        userRepository.changeGoal(chatId, callbackQuery.getData());
        chatStateRepository.changeState(chatId, DialogState.ENTERMENU.toString());
        double norm = calculateNorm(chatId);
        String text = "Твоя суточная норма: " + norm + " ккал.";
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(text);
        response.setReplyMarkup(setMenu());

        try {
            execute(response);
            logger.info("Sent message \"{}\" to {}", text, chatId);
            System.out.println("aaaaaaaaaaaaaaaaaaaa");
            chatStateRepository.changeState(chatId, DialogState.ENTERMENU.toString());
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb");
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
        }
    }

    public ReplyKeyboardMarkup setMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Получить меню"));
        row1.add(new KeyboardButton("Моя анкета"));
        row2.add(new KeyboardButton("Мои продукты-исключения"));
        row2.add(new KeyboardButton("Изменить продукты-исключения"));
        row3.add(new KeyboardButton("Пройти анкетирование"));

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }


    public double calculateNorm(Long chatId) {
        int weight = Integer.parseInt(userRepository.getWeightById(chatId));
        int age = Integer.parseInt(userRepository.getAgeById(chatId));
        int height = Integer.parseInt(userRepository.getHeightById(chatId));
        double activity = Double.parseDouble(userRepository.getActivityById(chatId));
        int cel = Integer.parseInt(userRepository.getGoalById(chatId));
        double imt = weight * 10000 / (height * height);
        String im = Double.toString(imt);
        userRepository.changeImt(chatId, im);
        double norm = (10 * weight + 6.25 * height - 5 * age) * activity;
        if (cel == 1) norm *= 0.8;
        else if (cel == 3) norm *= 1.2;
        String n = Double.toString(norm);
        userRepository.changeCalorie(chatId, n);
        return norm;
    }

    public void handlerMenu(Long chatId, Message message) {
        if (message.getText().equals("Моя анкета")) {
            String name = userRepository.getNameById(chatId);
            String age = userRepository.getAgeById(chatId);
            String height = userRepository.getHeightById(chatId);
            String weight = userRepository.getWeightById(chatId);
            String goal = userRepository.getGoalById(chatId);
            String activ = userRepository.getActivityById(chatId);
            String norm = userRepository.getcalorieIntakeById(chatId);
            String text = "Имя: " + name + "\n" +
                    "Возраст: "  + age + "\n" +
                    "Рост: " + height + "\n" +
                    "Вес: " + weight + "\n" +
                    "Цель: похудеть"  + "\n" +
                    "Активность: 1.2"  + "\n" +
                    "Суточная норма: "  + norm + " ккал.\n" ;
            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);

            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
        else if(message.getText().equals("Получить меню")){
            String text = "Завтрак: " + "\n" + "250 г овсяной каши на молоке, 50 г кураги, стакан зеленого чая с сахаром, банан." + "\n" +
                    "Обед: " + "\n" + "250 г супа (зеленый горошек, морковь, картофель отварить и измельчить в блендере) со сливками, 150 г макрон из твердых сортов пшеницы, 100 г запеченой куриной грудки, морс из ягод." + "\n" +
                     "Ужин: " + "\n" + "200 г овощного салата из огурцов, болгарского перца, помидора и редиса с зеленью, заправленный оливковым маслом, 1 яйцо." + "\n";
            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);

            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }
}


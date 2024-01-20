package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;


public class PostController {
    private final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson = new Gson();


    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        // TODO: deserialize request & serialize response
        final var data = service.getById(id);
        printRespons(data, response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        printRespons(data, response);
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        // TODO: deserialize request & serialize response
        service.removeById(id);
        response.getWriter().print("Успешно удалено");
    }

    public void printRespons(Post data, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        try {
            response.getWriter().print(gson.toJson(data));
        } catch (IOException e) {
            throw new NotFoundException("Исключение ввода или вывода");
        }
    }
}

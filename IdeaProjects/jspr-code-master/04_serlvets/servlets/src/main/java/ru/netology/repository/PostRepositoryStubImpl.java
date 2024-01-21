package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentMap<Long, Post> list = new ConcurrentHashMap<>();
    private final static AtomicLong NAMBER_ID = new AtomicLong(0);

    public Collection<Post> all() {
        return list.values();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            Long newId = NAMBER_ID.incrementAndGet();
            post.setId(newId);
            list.put(newId, post);
        }
        if (list.containsKey(post.getId())) {
            list.put(post.getId(), post);
        } else {
            throw new NotFoundException("Элемент обновить не получилось");
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        if (!list.containsKey(id)) {
            throw new NotFoundException("Такого id нет");
        }
        list.remove(id);
    }
}

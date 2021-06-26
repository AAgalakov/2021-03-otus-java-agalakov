package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.homework.Originator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Originator> longOriginatorHashMap = new HashMap<>();
    private final Originator originator = new Originator(LocalDateTime::now);

    @Override
    public void onUpdated(Message msg) {
        originator.saveState(msg);
        longOriginatorHashMap.put(msg.getId(), originator);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(longOriginatorHashMap.get(id)).map(Originator::restoreState);
    }
}

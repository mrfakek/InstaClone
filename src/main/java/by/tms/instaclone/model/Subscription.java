package by.tms.instaclone.model;

import by.tms.instaclone.settings.TimeZoneSettings;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Subscription {

    //userUuid: идентификатор Пользователя- к которому относятся рекомендации
    //userList: список рекомендованных пользователей

    private final UUID uuid;                // идентификатор Подписки
    private final User subscriber;          //  подписавшейся Пользователь
    private final User publisher;           //  Пользователь-публикатор на кого подписался
    private final LocalDateTime createAt;   // время создания Подписки

    public Subscription(User subscriber, User publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
        this.createAt = LocalDateTime.now(TimeZoneSettings.getUtcClock());
        this.uuid = UUID.nameUUIDFromBytes((subscriber.getUuid().toString() + publisher.getUuid().toString() + this.createAt).getBytes());
    }

    /**
     * Пересоздание (ранее созданного/существующего) Subscription
     *
     * @param uuid
     * @param subscriber
     * @param publisher
     * @param createAt
     */
    public Subscription(UUID uuid, User subscriber, User publisher, LocalDateTime createAt) {
        this.uuid = uuid;
        this.subscriber = subscriber;
        this.publisher = publisher;
        this.createAt = createAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public User getPublisher() {
        return publisher;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}

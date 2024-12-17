package org.tennis_bird.api.chat;

import java.util.List;
import java.util.UUID;

public record CreateChatRequest(String name, List<UUID> personIds) {}

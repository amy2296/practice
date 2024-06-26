package com.Demo.Example.Record;

import lombok.Builder;

@Builder
public record Mailbody
        (String to,String subject,String text) {
}

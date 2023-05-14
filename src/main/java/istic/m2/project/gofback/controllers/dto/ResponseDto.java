package istic.m2.project.gofback.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import istic.m2.project.gofback.exceptions.MessageError;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class ResponseDto<T extends Serializable> implements Serializable {

    @Getter
    private T data;
    private boolean success = true;
    private MessageDto error = null;

    private List<MessageDto> warnings = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PagingDto pagination = null;

    public ResponseDto(T data) {
        super();
        this.data = data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageDto implements Serializable {
        private MessageError messageCode;
        private String message;
    }

    @Getter
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PagingDto implements Serializable {
        private Integer count;
        @JsonProperty("content-range")
        private String contentRange;
        @JsonProperty("accept-range")
        private String acceptRange;
        @JsonProperty("_links")
        private Map<String, LinkDto> links;

        public HttpHeaders toHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_RANGE, contentRange);
            headers.set(HttpHeaders.ACCEPT_RANGES, acceptRange);
            headers.set(HttpHeaders.LINK, getLink(links));
            return headers;
        }

        private String getLink(Map<String, LinkDto> links) {
            return links.entrySet().stream()
                    .map(kv -> kv.getValue().getHref() + ";ref=\"" + kv.getKey() + "\"")
                    .collect(Collectors.joining(", "));
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkDto implements Serializable {
        private String href;
    }

}

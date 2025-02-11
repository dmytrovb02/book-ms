package ua.andersen.library.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.andersen.library.entity.AuthorDetails;

@FeignClient(name = "authorClient", url = "https://example.com/api/authors")
public interface AuthorClient {

    @GetMapping("/{authorName}")
    AuthorDetails getAuthorDetails(@PathVariable String authorName);
}

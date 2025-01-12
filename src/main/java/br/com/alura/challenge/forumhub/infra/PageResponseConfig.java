package br.com.alura.challenge.forumhub.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Padroniza a serialização da resposta que utiliza paginação, alterando para serialização usando {@link org.springframework.data.web.PagedModel PagedModel}
 * que é mais adequada e estável do que usar diretamente {@link org.springframework.data.domain.PageImpl PageImpl} .
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PageResponseConfig {

}

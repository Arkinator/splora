package com.github.arkinator.splora.model;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ExtractBeanNameTest {
    @Test
    public void shouldExtractSimpleCase() {
        assertThat(SploraClass.extractBeanNameFromPart("\n    private SophoraRequestBuilderService sophoraRequestBuilderService;"))
                .isEqualTo("SophoraRequestBuilderService");
    }

    @Test
    public void shouldExtractNoVisibilityCase() {
        assertThat(SploraClass.extractBeanNameFromPart("\n    SophoraRequestBuilderService sophoraRequestBuilderService;"))
                .isEqualTo("SophoraRequestBuilderService");
    }
}
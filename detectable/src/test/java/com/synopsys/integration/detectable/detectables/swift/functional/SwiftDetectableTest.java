package com.synopsys.integration.detectable.detectables.swift.functional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

import com.synopsys.integration.bdio.model.Forge;
import com.synopsys.integration.detectable.Detectable;
import com.synopsys.integration.detectable.DetectableEnvironment;
import com.synopsys.integration.detectable.Extraction;
import com.synopsys.integration.detectable.detectable.exception.DetectableException;
import com.synopsys.integration.detectable.detectable.executable.ExecutableOutput;
import com.synopsys.integration.detectable.detectable.executable.resolver.SwiftResolver;
import com.synopsys.integration.detectable.functional.DetectableFunctionalTest;
import com.synopsys.integration.detectable.util.graph.NameVersionGraphAssert;

public class SwiftDetectableTest extends DetectableFunctionalTest {

    public SwiftDetectableTest() throws IOException {
        super("swift");
    }

    @Override
    protected void setup() throws IOException {

        addFile(Paths.get("Package.swift"));

        ExecutableOutput rootSwiftPackage = createStandardOutput(
            "{",
            "   \"name\": \"DeckOfPlayingCards\",",
            "   \"url\": \"/Users/jakem/bazelWorkspace/sleuthifer/swift/example-package-deckofplayingcards\",",
            "   \"version\": \"unspecified\",",
            "   \"path\": \"/Users/jakem/bazelWorkspace/sleuthifer/swift/example-package-deckofplayingcards\",",
            "   \"dependencies\": [",
            "       {",
            "           \"name\": \"FisherYates\",",
            "           \"url\": \"https://github.com/apple/example-package-fisheryates.git\",",
            "           \"version\": \"2.0.5\",",
            "           \"path\": \"/Users/jakem/bazelWorkspace/sleuthifer/swift/example-package-deckofplayingcards/.build/checkouts/example-package-fisheryates\",",
            "           \"dependencies\": []",
            "       },",
            "       {",
            "           \"name\": \"PlayingCard\",",
            "           \"url\": \"https://github.com/apple/example-package-playingcard.git\",",
            "           \"version\": \"3.0.5\",",
            "           \"path\": \"/Users/jakem/bazelWorkspace/sleuthifer/swift/example-package-deckofplayingcards/.build/checkouts/example-package-playingcard\",",
            "           \"dependencies\": []",
            "       }",
            "   ]",
            "}"
        );
        addExecutableOutput(rootSwiftPackage, "swift", "package", "show-dependencies", "--format", "json");
    }

    @NotNull
    @Override
    public Detectable create(@NotNull final DetectableEnvironment detectableEnvironment) {
        return detectableFactory.createSwiftCliDetectable(detectableEnvironment, new SwiftResolver() {
            @Override
            public File resolveSwift() throws DetectableException {
                return new File("swift");
            }
        });
    }

    @Override
    public void assertExtraction(@NotNull final Extraction extraction) {
        Assertions.assertNotEquals(0, extraction.getCodeLocations().size());

        NameVersionGraphAssert graphAssert = new NameVersionGraphAssert(Forge.COCOAPODS, extraction.getCodeLocations().get(0).getDependencyGraph());
        graphAssert.hasRootSize(2);
        graphAssert.hasRootDependency("FisherYates", "2.0.5");
        graphAssert.hasRootDependency("PlayingCard", "3.0.5");
    }
}

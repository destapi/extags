package works.hop.eztag.parser;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JParserTest {

    @Test
    void test_loading_and_parsing_websites_xml() {
        String file = "/phase1/websites.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        System.out.println(processor.process(root));
    }

    @Test
    void test_loading_and_parsing_template_tags_xml() {
        String file = "/phase1/template-tags.xml";
        JContext processor = new JContext(Map.of("name", "Cassie", "visible", true, "over", false));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(3);
        assertThat(processor.process(root)).isEqualTo("<p class=\"bg-dark\"><span class=\"title\">Cassie</span><i class=\"fa fa-check\"></i></p>");
    }

    @Test
    void test_loading_and_parsing_template_tags_2_xml() {
        String file = "/phase1/template-tags2.xml";
        JContext processor = new JContext(Map.of("name", "Jimbob", "visible", true, "over", false));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(2);
        assertThat(processor.process(root)).isEqualTo("<p class=\"fa fa-check\">Yepee<span class=\"title\">Jimbob</span></p>");
    }

    @Test
    void test_loading_and_parsing_template_tags_3_xml() {
        String file = "/phase1/template-tags3.xml";
        JContext processor = new JContext(Map.of("name", "Jimbob", "visible", true, "over", false));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(0);
        assertThat(processor.process(root)).isEqualTo("<p class=\"fa fa-memo\">My name is Jimbob</p>");
    }

    @Test
    void test_loading_and_parsing_x_show_attr_xml() {
        String file = "/phase1/x-show-attr.xml";
        JContext processor = new JContext(Map.of("title", "Read book", "done", false));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(4);
        assertThat(processor.process(root)).isEqualTo("<li><i data-x-show=\"false\" title=\"done\" class=\"fa fa-square\"></i><i data-x-show=\"true\" title=\"done\" class=\"fa fa-check-square\"></i><span>Read book</span><i title=\"remove\" class=\"fa fa-times-circle\"></i></li>");
    }

    @Test
    void test_loading_and_parsing_template_tags_4_xml() {
        String file = "/phase1/template-tags4.xml";
        JContext processor = new JContext(Map.of("name", "Jimbob", "visible", true, "over", false));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(1);
        assertThat(processor.process(root)).isEqualTo("<p class=\"me\"><p class=\"fa fa-memo\">My name is Jimbob</p></p>");
    }

    @Test
    void test_loading_and_parsing_layout_template_xml() {
        String file = "/phase1/layout-template.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(2);
        assertThat(root.slots).hasSize(3);
        assertThat(processor.process(root)).isEqualTo("<html lang=\"en\"><head><meta charset=\"UTF-8\"/><meta name=\"viewport\" content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\"/><meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\"/><title>Default Title</title></head><body><nav><menu><li>Login</li></menu></nav><main></main><footer><div><span class=\"sticky\">&copy; 2024 EzTag</span></div></footer><script src=\"special-sauce.js\" type=\"module\"></script></body></html>");
    }

    @Test
    void test_loading_and_parsing_page_decorated_with_layout_xml() {
        String file = "/phase1/decorated-page.xml";
        JContext processor = new JContext(Map.of("name", "Jimbob", "visible", true, "over", false,
                "page", Map.of("title", "The best title"),
                "items",
                List.of(Map.of("id", "1", "title", "Read book", "done", true),
                        Map.of("id", 2, "title", "Make pancakes", "done", false))));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(6);
        String markup = processor.process(root);
        assertThat(markup).isEqualTo("<!doctype html><html lang=\"en\"><head><meta charset=\"UTF-8\"/><meta name=\"viewport\" content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\"/><meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\"/><meta hobbby=\"reading\"/><link rel=\"stylesheet\" hre=\"css/style.css\" type=\"text/css\"/><title>The best title</title><script defer src=\"/js/sauce.js\"></script></head><body><nav><menu><li>Login</li></menu></nav><div id=\"todo-list\"><form onsubmit=\"add\"><label><input name=\"title\" onchange=\"edit\"/></label><button type=\"submit\">Add</button></form><ul><li data-x-id=\"1\"><i title=\"done\" class=\"fa fa-square\"></i><span>Read book</span><i title=\"remove\" class=\"fa fa-times-circle\"></i></li><li data-x-id=\"2\"><i title=\"done\" class=\"fa fa-check-square\"></i><span>Make pancakes</span><i title=\"remove\" class=\"fa fa-times-circle\"></i></li></ul><p>2</p></div><footer><div><span class=\"sticky\">&copy; 2024 EzTag</span></div></footer><script src=\"special-sauce.js\" type=\"module\"></script></body></html>");
    }

    @Test
    void test_loading_and_parsing_page_decorated_with_basic_layout_xml() {
        String file = "/phase1/basic-page.xml";
        JContext processor = new JContext(Map.of("page", Map.of("title", "The best title")));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(9);
        assertThat(processor.process(root)).isEqualTo("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"/><meta another=\"something\" hobbby=\"programming\"/><meta skylight=\"azure\" baseline=\"yellow\"/><link rel=\"stylesheet\" hre=\"css/reset.css\" type=\"text/css\"/><link rel=\"stylesheet\" hre=\"css/style.css\" type=\"text/css\"/><title>The best title</title><script defer src=\"/js/hot-sauce.js\"></script><script defer src=\"/js/sweet-sauce.js\"></script></head><body><div id=\"todo-list\">I'm here</div></body></html>");
    }

    @Test
    void test_loading_and_parsing_plain_tags_xml() {
        String file = "/phase1/plain-tags.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(2);
        assertThat(processor.process(root)).isEqualTo("<p id=\"name\"><span class=\"title\">Jimmy</span><i class=\"fa fa-check\"></i></p>");
    }

    @Test
    void test_loading_and_parsing_todolist_xml() {
        String file = "/phase1/todo-list.xml";
        JContext processor = new JContext(Map.of("items",
                List.of(Map.of("id", 1, "title", "Read book", "done", true),
                        Map.of("id", 2, "title", "Make pancakes", "done", false))));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(3);
        String markup = processor.process(root);
        System.out.println(markup);
        assertThat(markup).isEqualTo("<div id=\"todo-list\"><form onsubmit=\"add\"><label><input name=\"title\" onchange=\"edit\"/></label><button type=\"submit\">Add</button></form><ul><li data-x-id=\"1\"><i title=\"done\" class=\"fa fa-square\"></i><span>Read book</span><i title=\"remove\" class=\"fa fa-times-circle\"></i></li><li data-x-id=\"2\"><i title=\"done\" class=\"fa fa-check-square\"></i><span>Make pancakes</span><i title=\"remove\" class=\"fa fa-times-circle\"></i></li></ul><p>2</p></div>");
    }

    @Test
    void test_loading_and_parsing_markup_having_free_form_content() {
        String file = "/phase1/free-form-content.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(2);
        assertThat(processor.process(root)).isEqualTo("<head><style type=\"text/css\">.main {\n" +
                "        background-color: blue;color: white;\n" +
                "        font-size: 1.2em;}</style><script type=\"module\">export default {\n" +
                "        const a = 1;const b = 2\n" +
                "        const c = a + b;console.log(`sum of ${a} and ${b} = ${c}`)\n" +
                "        }</script></head>");
    }

    @Test
    void verify_that_tag_attribute_are_in_generated_markup_for_list_component() {
        String file = "/phase2/listing.xml";
        JContext processor = new JContext(Map.of("todos", List.of(Map.of("id", 1, "title", "Make coffee", "done", false))));
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(1);
        assertThat(processor.process(root)).isEqualTo("<ul id=\"todo-list\" class=\"listing\"><li data-x-id=\"1\"><label><input type=\"checkbox\"/></label><span>Make coffee</span></li></ul>");
    }

    @Test
    void verify_that_named_slots_can_also_be_imported_into_target_page() {
        String file = "/phase2/page-not-importing-slots.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(3);
        String markup = processor.process(root);
        assertThat(markup).isEqualTo("<body><section id=\"header-slot\"><div>Preferred header content</div></section><article id=\"content-slot\"><div>Preferred content content</div></article><section id=\"footer-slot\"><div>Preferred footer content</div></section></body>");

        String file2 = "/phase2/page-importing-named-slots.xml";
        JParser parser2 = new JParser(file2, processor);
        JElement root2 = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root2.children).hasSize(3);
        String markup2 = processor.process(root2);
        assertThat(markup2).isEqualTo(markup);
    }

    @Test
    void verify_that_fragments_in_a_page_are_stored_in_a_memory_map() {
        String file = "/phase2/page-declaring-fragments.xml";
        JContext processor = new JContext(emptyMap());
        JParser parser = new JParser(file, processor);
        JElement root = assertDoesNotThrow(() -> parser.parse(), "Not expecting error to be thrown");
        assertThat(root.children).hasSize(3);
        assertThat(root.fragments).hasSize(3);
        assertThat(processor.process(root)).isEqualTo("<div><section id=\"header-slot\"><div>Preferred header content</div></section><article id=\"content-slot\"><div>Preferred content content</div></article><section id=\"footer-slot\"><div>Preferred footer content</div></section></div>");
        assertThat(processor.process(root.fragments.get("header"))).isEqualTo("<section id=\"header-slot\"><div>Preferred header content</div></section>");
        assertThat(processor.process(root.fragments.get("content"))).isEqualTo("<article id=\"content-slot\"><div>Preferred content content</div></article>");
        assertThat(processor.process(root.fragments.get("footer"))).isEqualTo("<section id=\"footer-slot\"><div>Preferred footer content</div></section>");
    }
}
package works.hop.eztag.parser;

import org.mvel2.MVEL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class JParser {

    String path;
    JContext processor;
    Stack<JElement> stack = new Stack<>();
    JElement lastPopped;

    public JParser(JContext processor) {
        this.processor = processor;
    }

    public JParser(String path, JContext processor) {
        this.path = path;
        this.processor = processor;
    }

    public XMLEventReader reader() throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        return xmlInputFactory.createXMLEventReader(
                getClass().getResourceAsStream(Objects.requireNonNull(path, "you must either initialize the file path variable " +
                        "in the constructor, or try to use a different parse function that meets your requirements better")));
    }

    public XMLEventReader reader(Reader content) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        return xmlInputFactory.createXMLEventReader(content);
    }

    public JElement parse() throws XMLStreamException {
        parse(reader());
        lastPopped.observer(processor);
        return lastPopped;
    }

    public JElement parse(String content) throws XMLStreamException {
        return parse(new StringReader(content));
    }

    public JElement parse(Reader content) throws XMLStreamException {
        parse(reader(content));
        lastPopped.observer(processor);
        return lastPopped;
    }

    private void parse(XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                JElement element = new JElement();

                // add parent-child relationship
                if (!stack.isEmpty()) {
                    stack.peek().children.add(element);
                    element.parent(stack.peek());
                }

                // introspect element
                StartElement startElement = nextEvent.asStartElement();
                String startTag = startElement.getName().getLocalPart();
                element.setTagName(startTag);
                if (startTag.startsWith("x-")) {
                    element.setComponent(true);
                    for (Iterator<Attribute> iterator = startElement.getAttributes(); iterator.hasNext(); ) {
                        Attribute attribute = iterator.next();
                        String attrName = attribute.getName().getLocalPart();
                        String attrValue = attribute.getValue();
                        // different attributes require different handling
                        switch (attrName) {
                            case "x-if": {
                                element.setIfExpression(attrValue);
                                break;
                            }
                            case "x-show": {
                                element.setShowExpression(attrValue);
                                element.attributes.put("data-x-show", attrValue);
                                break;
                            }
                            case "x-items": {
                                element.setListExpression(attrValue);
                                break;
                            }
                            case "x-key": {
                                element.setListItemsKey(attrValue);
                                break;
                            }
                            case "x-text": {
                                element.setTextExpression(attrValue);
                                break;
                            }
                            case "x-path": {
                                element.setIncludePath(attrValue);
                                break;
                            }
                            case "x-eval": {
                                element.setEvalNode((Boolean) MVEL.eval(attrValue));
                                break;
                            }
                            case "x-slot": {
                                element.setLayoutSlot(true);
                                element.setSlotRef(attrValue);
                                ((JElement) element.root()).slots.put(attrValue, element);
                                break;
                            }
                            case "x-named": {
                                element.setSlotNode(true);
                                element.setSlotName(attrValue);
                                break;
                            }
                            case "x-fragment": {
                                element.setFragmentNode(true);
                                element.setFragmentRef(attrValue);
                                ((JElement) element.root()).fragments.put(attrValue, element);
                                break;
                            }
                            case "x-template": {
                                element.setLayoutNode(true);
                                element.setTemplatePath(attrValue);
                                break;
                            }
                            case "x-doctype": {
                                ((JElement) element.root()).setDocTypeTag(attrValue);
                                break;
                            }
                            case "x-sub": {
                                element.interests.addAll(Arrays.stream(attrValue.split(",")).toList());
                                break;
                            }
                            default: {
                                element.attributes.put(attrName, attrValue);
                                break;
                            }
                        }
                    }
                } else {
                    for (Iterator<Attribute> iterator = startElement.getAttributes(); iterator.hasNext(); ) {
                        Attribute attribute = iterator.next();
                        String attrName = attribute.getName().getLocalPart();
                        String attrValue = attribute.getValue();
                        element.attributes.put(attrName, attrValue);
                    }
                }

                // push a new element into the stack
                stack.push(element);
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                String endTag = endElement.getName().getLocalPart();
                JElement tail = stack.peek();
                //expecting tail to match end tag
                if (Objects.requireNonNull(tail).getTagName().equals(endTag)) {
                    lastPopped = stack.pop();
                } else {
                    throw new RuntimeException("Expected to get end of <" + endTag + "> tag");
                }
            }

            if (nextEvent.isCharacters()) {
                String data = nextEvent.asCharacters().getData().trim();
                if (!data.isEmpty()) {
                    if (stack.peek().isEvalNode) {
                        stack.peek().setEvalContent(data);
                    } else {
                        JElement textNode = new JElement();
                        textNode.setTextNode(true);
                        textNode.setTextContent(data);
                        Objects.requireNonNull(stack.peek()).children.add(textNode);
                    }
                }
            }
        }
    }
}

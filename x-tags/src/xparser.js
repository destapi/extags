const path = require('path');
const htmlparser = require('htmlparser2');

const url = path.resolve('x', 'first.html');

function newNode(tag){
    return ({
        tag,
        parent: null,
        children: [],
        attributes: [],
        textContent: null,
        isTextNode: false,
        isEvalNode: false,
        isComponent: false,
    })
}

function visitAttributes(node, attributes){
    console.log(node, attributes)
}

function parseFile(url) {
    return new Promise((resolve, reject) => {
        fs.readFile(url, options, (err, data) => {
            if (err) {
                console.error('Error:', err);
                reject(err);
                return;
            }

            const stack = [];
            let currentNode = {}; // To store the current node being processed

            const parser = new htmlparser.Parser({
                onopentag(name, attributes) {
                    currentNode = newNode(name)

                    if(stack.length > 0){
                        let peek = stack[stack.length - 1];
                        peek.children.push(currentNode)
                        currentNode.parent = peek;
                    }

                    if(name.startsWith("x-")){
                        currentNode.isComponent = true;
                        visitAttributes(currentNode, attributes);
                    }
                    else{
                        visitAttributes(currentNode, attributes);
                    }
                },
                ontext(text) {
                    if(text){
                        if(currentNode.isEvalNode){
                            console.log('eval the text value')
                        }
                        else{
                            let textNode = newNode();
                            textNode.isTextNode = true;
                            textNode.textContent = text;
                            stack[stack.length - 1].children.push(textNode);
                        }
                    }
                },
                onclosetag(name) {
                    let tail = stack[stack.length - 1];
                    if(tail === name){
                        stack = stack.pop()
                    }
                    else{
                        throw Error(`expected to encounter end of ${name} tag`)
                    }
                }
            }, { decodeEntities: true });

            parser.write(response.body);
            parser.end();

            console.log('Books:');
            books.forEach((book, index) => {
                console.log(`${index + 1}. Title: ${book.title}, Price: ${book.price}`);
            });
        });
    })
}
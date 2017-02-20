describe("Test TextHandler", function() {
    beforeEach(function() {});
    afterEach (function() {});

    it("inspect TextHandler object structure", function() {
        // given
        new TextHandler();
        
        var givenTextHandlerStructure = [ 'constructor', 'getElementID', 'getSearchTextByElementId', 'getText',
                'setElementID', 'setText' ],
            expectedTextHandlerStructure;

        // when
        expectedTextHandlerStructure = Object.getOwnPropertyNames(TextHandler.prototype).sort();

        // then
        expect(expectedTextHandlerStructure).toEqual(givenTextHandlerStructure);
    });
    it("inspect TextHandler.setText() method structure", function() {
        // given
        var givenSetTextProperties = [ 'elementID', 'text' ];

        // when
        var textHandler = new TextHandler();
        var expectedSetTextProperties = Object.getOwnPropertyNames(textHandler).sort();

        // then
        expect(expectedSetTextProperties).toEqual(givenSetTextProperties);
    });
    it("inspect TextHandler.setText method", function() {
        // given
        var givenText = 'simple text';

        // when
        var textHandler = new TextHandler();
            textHandler.setText(givenText);
        
        var expectedSetTextResult = textHandler.text;

        // then
        expect(expectedSetTextResult).toEqual(givenText);
    });
    it("is TextHandler.getText() method defineed", function() {
        // given

        // when
        var textHandler = new TextHandler();

        // then
        expect(textHandler.getText).toBeDefined();
    });
    it("inspect TextHandler.setText method", function() {
        // given
        var givenElementID = 'simple text';

        // when
        var textHandler = new TextHandler();
        textHandler.setElementID(givenElementID);

        var expectedSetTextResult = textHandler.elementID;

        // then
        expect(expectedSetTextResult).toEqual(givenElementID);
    });
    it("is TextHandler.getText() method defineed", function() {
        // given

        // when
        var textHandler = new TextHandler();

        // then
        expect(textHandler.getElementID).toBeDefined();
    });
    it("calls the getText() function", function() {
        // given
        var givenText = 'simple text';
        var elementID = 'searchText';
        var textareaElement = document.createElement('textarea');
            textareaElement.setAttribute("id", elementID);
            textareaElement.value = givenText;

        document.getElementById = jasmine.createSpy('HTML Element').and.returnValue(textareaElement);

        // when
        var textHandler = new TextHandler();
            textHandler.setElementID(elementID);

        var expectedText = textHandler.getText();

        // then
        expect(expectedText).toEqual(givenText);
    });
});

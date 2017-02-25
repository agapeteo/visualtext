describe("Test TextHandler", function() {
    beforeEach(function() {});
    afterEach (function() {});

    it("inspect TextHandler.getText method", function() {
        // given
        var givenText = 'simple text';

        // when
        var textHandler = new TextHandler();
            textHandler.setText(givenText);
        
        var expectedSetTextResult = textHandler.getText();

        // then
        expect(expectedSetTextResult).toEqual(givenText);
    });
    it("inspect TextHandler.getElementID() method", function() {
        // given
        var givenText = 'simple text';

        // when
        var textHandler = new TextHandler();
            textHandler.setElementID(givenText);

        var expectedSetTextResult = textHandler.getElementID();

        // then
        expect(expectedSetTextResult).toEqual(givenText);
    });
});

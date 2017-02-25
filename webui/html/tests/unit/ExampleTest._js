describe("Test VisualTextController", function() {
    // given
    var givenVisualTextControllerStructure = ["constructor", "getRequest", "getText", "publishResponse", "run", "sendRequest",
        "setPublisher", "setRequestBuilder", "setResponse", "setTextHandler", "setTransport"],
        expectedVisualTextControllerStructure;
    
    beforeEach(function() {
        new VisualTextController();
    });
    afterEach (function() {});

    it("inspect VisualTextController object structure", function() {
        // when
        expectedVisualTextControllerStructure = Object.getOwnPropertyNames(VisualTextController.prototype).sort();

        // then
        expect(expectedVisualTextControllerStructure).toEqual(givenVisualTextControllerStructure);
    });
});

'use strict';

describe('Controller Tests', function() {

    describe('PersonAreaPercentage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonAreaPercentage, MockPerson, MockMediaType, MockAreaType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonAreaPercentage = jasmine.createSpy('MockPersonAreaPercentage');
            MockPerson = jasmine.createSpy('MockPerson');
            MockMediaType = jasmine.createSpy('MockMediaType');
            MockAreaType = jasmine.createSpy('MockAreaType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonAreaPercentage': MockPersonAreaPercentage,
                'Person': MockPerson,
                'MediaType': MockMediaType,
                'AreaType': MockAreaType
            };
            createController = function() {
                $injector.get('$controller')("PersonAreaPercentageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personAreaPercentageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

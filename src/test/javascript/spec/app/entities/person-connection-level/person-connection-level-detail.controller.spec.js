'use strict';

describe('Controller Tests', function() {

    describe('PersonConnectionLevel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonConnectionLevel, MockPerson, MockConnectionLevel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonConnectionLevel = jasmine.createSpy('MockPersonConnectionLevel');
            MockPerson = jasmine.createSpy('MockPerson');
            MockConnectionLevel = jasmine.createSpy('MockConnectionLevel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonConnectionLevel': MockPersonConnectionLevel,
                'Person': MockPerson,
                'ConnectionLevel': MockConnectionLevel
            };
            createController = function() {
                $injector.get('$controller')("PersonConnectionLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personConnectionLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

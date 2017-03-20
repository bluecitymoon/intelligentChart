'use strict';

describe('Controller Tests', function() {

    describe('PersonFansEgeLevel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonFansEgeLevel, MockPerson, MockEgeLevel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonFansEgeLevel = jasmine.createSpy('MockPersonFansEgeLevel');
            MockPerson = jasmine.createSpy('MockPerson');
            MockEgeLevel = jasmine.createSpy('MockEgeLevel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonFansEgeLevel': MockPersonFansEgeLevel,
                'Person': MockPerson,
                'EgeLevel': MockEgeLevel
            };
            createController = function() {
                $injector.get('$controller')("PersonFansEgeLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personFansEgeLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

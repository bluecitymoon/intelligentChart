'use strict';

describe('Controller Tests', function() {

    describe('PersonPopularity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonPopularity, MockPerson, MockPopularityType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonPopularity = jasmine.createSpy('MockPersonPopularity');
            MockPerson = jasmine.createSpy('MockPerson');
            MockPopularityType = jasmine.createSpy('MockPopularityType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonPopularity': MockPersonPopularity,
                'Person': MockPerson,
                'PopularityType': MockPopularityType
            };
            createController = function() {
                $injector.get('$controller')("PersonPopularityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personPopularityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

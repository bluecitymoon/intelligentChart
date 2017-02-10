'use strict';

describe('Controller Tests', function() {

    describe('PersonRegionConnection Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonRegionConnection, MockPerson, MockRegion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonRegionConnection = jasmine.createSpy('MockPersonRegionConnection');
            MockPerson = jasmine.createSpy('MockPerson');
            MockRegion = jasmine.createSpy('MockRegion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonRegionConnection': MockPersonRegionConnection,
                'Person': MockPerson,
                'Region': MockRegion
            };
            createController = function() {
                $injector.get('$controller')("PersonRegionConnectionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personRegionConnectionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

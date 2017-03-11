'use strict';

describe('Controller Tests', function() {

    describe('PersonFansPucharsingPower Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonFansPucharsingPower, MockPerson, MockFansPurchasingSection;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonFansPucharsingPower = jasmine.createSpy('MockPersonFansPucharsingPower');
            MockPerson = jasmine.createSpy('MockPerson');
            MockFansPurchasingSection = jasmine.createSpy('MockFansPurchasingSection');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonFansPucharsingPower': MockPersonFansPucharsingPower,
                'Person': MockPerson,
                'FansPurchasingSection': MockFansPurchasingSection
            };
            createController = function() {
                $injector.get('$controller')("PersonFansPucharsingPowerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personFansPucharsingPowerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

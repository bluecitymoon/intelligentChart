'use strict';

describe('Controller Tests', function() {

    describe('PersonCreditCardActivity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonCreditCardActivity, MockPerson, MockCreditCardActivityType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonCreditCardActivity = jasmine.createSpy('MockPersonCreditCardActivity');
            MockPerson = jasmine.createSpy('MockPerson');
            MockCreditCardActivityType = jasmine.createSpy('MockCreditCardActivityType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonCreditCardActivity': MockPersonCreditCardActivity,
                'Person': MockPerson,
                'CreditCardActivityType': MockCreditCardActivityType
            };
            createController = function() {
                $injector.get('$controller')("PersonCreditCardActivityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personCreditCardActivityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

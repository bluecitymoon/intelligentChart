'use strict';

describe('Controller Tests', function() {

    describe('PersonFanPaymentTool Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonFanPaymentTool, MockPerson, MockPaymentTool;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonFanPaymentTool = jasmine.createSpy('MockPersonFanPaymentTool');
            MockPerson = jasmine.createSpy('MockPerson');
            MockPaymentTool = jasmine.createSpy('MockPaymentTool');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonFanPaymentTool': MockPersonFanPaymentTool,
                'Person': MockPerson,
                'PaymentTool': MockPaymentTool
            };
            createController = function() {
                $injector.get('$controller')("PersonFanPaymentToolDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personFanPaymentToolUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

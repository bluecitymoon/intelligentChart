'use strict';

describe('Controller Tests', function() {

    describe('PersonNetworkShopping Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonNetworkShopping, MockPerson, MockNetworkShoppingType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonNetworkShopping = jasmine.createSpy('MockPersonNetworkShopping');
            MockPerson = jasmine.createSpy('MockPerson');
            MockNetworkShoppingType = jasmine.createSpy('MockNetworkShoppingType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonNetworkShopping': MockPersonNetworkShopping,
                'Person': MockPerson,
                'NetworkShoppingType': MockNetworkShoppingType
            };
            createController = function() {
                $injector.get('$controller')("PersonNetworkShoppingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personNetworkShoppingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

'use strict';

describe('Controller Tests', function() {

    describe('PersonNetworkTexiActivity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonNetworkTexiActivity, MockPerson, MockNetworkTexiCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonNetworkTexiActivity = jasmine.createSpy('MockPersonNetworkTexiActivity');
            MockPerson = jasmine.createSpy('MockPerson');
            MockNetworkTexiCompany = jasmine.createSpy('MockNetworkTexiCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonNetworkTexiActivity': MockPersonNetworkTexiActivity,
                'Person': MockPerson,
                'NetworkTexiCompany': MockNetworkTexiCompany
            };
            createController = function() {
                $injector.get('$controller')("PersonNetworkTexiActivityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personNetworkTexiActivityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

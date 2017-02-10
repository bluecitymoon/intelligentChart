'use strict';

describe('Controller Tests', function() {

    describe('PersonPrize Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonPrize, MockPrizeType, MockPrizeGroup, MockPrizeLevel, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonPrize = jasmine.createSpy('MockPersonPrize');
            MockPrizeType = jasmine.createSpy('MockPrizeType');
            MockPrizeGroup = jasmine.createSpy('MockPrizeGroup');
            MockPrizeLevel = jasmine.createSpy('MockPrizeLevel');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonPrize': MockPersonPrize,
                'PrizeType': MockPrizeType,
                'PrizeGroup': MockPrizeGroup,
                'PrizeLevel': MockPrizeLevel,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("PersonPrizeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personPrizeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

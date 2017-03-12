'use strict';

describe('Controller Tests', function() {

    describe('PersonFansHobby Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonFansHobby, MockPerson, MockHobby;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonFansHobby = jasmine.createSpy('MockPersonFansHobby');
            MockPerson = jasmine.createSpy('MockPerson');
            MockHobby = jasmine.createSpy('MockHobby');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonFansHobby': MockPersonFansHobby,
                'Person': MockPerson,
                'Hobby': MockHobby
            };
            createController = function() {
                $injector.get('$controller')("PersonFansHobbyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personFansHobbyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

'use strict';

describe('Controller Tests', function() {

    describe('PersonInnovation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonInnovation, MockPerson, MockInnovationType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonInnovation = jasmine.createSpy('MockPersonInnovation');
            MockPerson = jasmine.createSpy('MockPerson');
            MockInnovationType = jasmine.createSpy('MockInnovationType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonInnovation': MockPersonInnovation,
                'Person': MockPerson,
                'InnovationType': MockInnovationType
            };
            createController = function() {
                $injector.get('$controller')("PersonInnovationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personInnovationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

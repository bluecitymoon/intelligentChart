'use strict';

describe('Controller Tests', function() {

    describe('PersonLawBusiness Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonLawBusiness, MockPerson, MockLawBusinessType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonLawBusiness = jasmine.createSpy('MockPersonLawBusiness');
            MockPerson = jasmine.createSpy('MockPerson');
            MockLawBusinessType = jasmine.createSpy('MockLawBusinessType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonLawBusiness': MockPersonLawBusiness,
                'Person': MockPerson,
                'LawBusinessType': MockLawBusinessType
            };
            createController = function() {
                $injector.get('$controller')("PersonLawBusinessDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personLawBusinessUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

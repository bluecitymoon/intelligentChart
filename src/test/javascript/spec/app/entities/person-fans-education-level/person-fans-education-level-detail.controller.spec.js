'use strict';

describe('Controller Tests', function() {

    describe('PersonFansEducationLevel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonFansEducationLevel, MockPerson, MockEducationLevel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonFansEducationLevel = jasmine.createSpy('MockPersonFansEducationLevel');
            MockPerson = jasmine.createSpy('MockPerson');
            MockEducationLevel = jasmine.createSpy('MockEducationLevel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonFansEducationLevel': MockPersonFansEducationLevel,
                'Person': MockPerson,
                'EducationLevel': MockEducationLevel
            };
            createController = function() {
                $injector.get('$controller')("PersonFansEducationLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personFansEducationLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

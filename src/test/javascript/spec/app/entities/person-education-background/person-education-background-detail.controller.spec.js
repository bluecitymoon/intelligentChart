'use strict';

describe('Controller Tests', function() {

    describe('PersonEducationBackground Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonEducationBackground, MockEducationBackgroundType, MockPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonEducationBackground = jasmine.createSpy('MockPersonEducationBackground');
            MockEducationBackgroundType = jasmine.createSpy('MockEducationBackgroundType');
            MockPerson = jasmine.createSpy('MockPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonEducationBackground': MockPersonEducationBackground,
                'EducationBackgroundType': MockEducationBackgroundType,
                'Person': MockPerson
            };
            createController = function() {
                $injector.get('$controller')("PersonEducationBackgroundDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personEducationBackgroundUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

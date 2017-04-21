'use strict';

describe('Controller Tests', function() {

    describe('MovieParticipant Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMovieParticipant, MockMovie, MockPerson, MockJob;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMovieParticipant = jasmine.createSpy('MockMovieParticipant');
            MockMovie = jasmine.createSpy('MockMovie');
            MockPerson = jasmine.createSpy('MockPerson');
            MockJob = jasmine.createSpy('MockJob');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MovieParticipant': MockMovieParticipant,
                'Movie': MockMovie,
                'Person': MockPerson,
                'Job': MockJob
            };
            createController = function() {
                $injector.get('$controller')("MovieParticipantDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:movieParticipantUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

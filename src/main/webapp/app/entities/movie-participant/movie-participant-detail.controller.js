(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieParticipantDetailController', MovieParticipantDetailController);

    MovieParticipantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MovieParticipant', 'Movie', 'Person', 'Job'];

    function MovieParticipantDetailController($scope, $rootScope, $stateParams, previousState, entity, MovieParticipant, Movie, Person, Job) {
        var vm = this;

        vm.movieParticipant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:movieParticipantUpdate', function(event, result) {
            vm.movieParticipant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

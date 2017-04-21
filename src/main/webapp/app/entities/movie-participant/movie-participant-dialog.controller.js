(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieParticipantDialogController', MovieParticipantDialogController);

    MovieParticipantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MovieParticipant', 'Movie', 'Person', 'Job'];

    function MovieParticipantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MovieParticipant, Movie, Person, Job) {
        var vm = this;

        vm.movieParticipant = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.movies = Movie.query();
        vm.people = Person.query();
        vm.jobs = Job.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.movieParticipant.id !== null) {
                MovieParticipant.update(vm.movieParticipant, onSaveSuccess, onSaveError);
            } else {
                MovieParticipant.save(vm.movieParticipant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:movieParticipantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

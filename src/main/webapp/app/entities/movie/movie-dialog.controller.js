(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieDialogController', MovieDialogController);

    MovieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Movie'];

    function MovieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Movie) {
        var vm = this;

        vm.movie = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.movie.id !== null) {
                Movie.update(vm.movie, onSaveSuccess, onSaveError);
            } else {
                Movie.save(vm.movie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:movieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.runDate = false;
        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

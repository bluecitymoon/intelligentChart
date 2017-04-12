(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubleMovieSubjectDialogController', DoubleMovieSubjectDialogController);

    DoubleMovieSubjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DoubleMovieSubject'];

    function DoubleMovieSubjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DoubleMovieSubject) {
        var vm = this;

        vm.doubleMovieSubject = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.doubleMovieSubject.id !== null) {
                DoubleMovieSubject.update(vm.doubleMovieSubject, onSaveSuccess, onSaveError);
            } else {
                DoubleMovieSubject.save(vm.doubleMovieSubject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:doubleMovieSubjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

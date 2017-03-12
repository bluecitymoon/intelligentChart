(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SexDialogController', SexDialogController);

    SexDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sex'];

    function SexDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sex) {
        var vm = this;

        vm.sex = entity;
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
            if (vm.sex.id !== null) {
                Sex.update(vm.sex, onSaveSuccess, onSaveError);
            } else {
                Sex.save(vm.sex, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:sexUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

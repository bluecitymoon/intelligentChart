(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('AreaTypeDialogController', AreaTypeDialogController);

    AreaTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AreaType'];

    function AreaTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AreaType) {
        var vm = this;

        vm.areaType = entity;
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
            if (vm.areaType.id !== null) {
                AreaType.update(vm.areaType, onSaveSuccess, onSaveError);
            } else {
                AreaType.save(vm.areaType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:areaTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

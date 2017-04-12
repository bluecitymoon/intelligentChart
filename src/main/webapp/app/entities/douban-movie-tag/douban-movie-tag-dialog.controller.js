(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubanMovieTagDialogController', DoubanMovieTagDialogController);

    DoubanMovieTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DoubanMovieTag'];

    function DoubanMovieTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DoubanMovieTag) {
        var vm = this;

        vm.doubanMovieTag = entity;
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
            if (vm.doubanMovieTag.id !== null) {
                DoubanMovieTag.update(vm.doubanMovieTag, onSaveSuccess, onSaveError);
            } else {
                DoubanMovieTag.save(vm.doubanMovieTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:doubanMovieTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
